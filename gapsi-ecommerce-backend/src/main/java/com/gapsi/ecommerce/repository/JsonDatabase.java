package com.gapsi.ecommerce.repository;

import com.gapsi.ecommerce.model.Provider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Repositorio para gestionar proveedores en archivo JSON
 * Patrón: Repository Pattern
 * Thread-safe usando ReadWriteLock para operaciones concurrentes
 */
@Repository
public class JsonDatabase {

    private static final Logger logger = LoggerFactory.getLogger(JsonDatabase.class);
    private final String DB_FILE_PATH;
    private final Gson gson;
    private final ReadWriteLock lock;

    public JsonDatabase() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.lock = new ReentrantReadWriteLock();

        // Obtener la ruta del directorio del proyecto
        String userDir = System.getProperty("user.dir");

        // Si estamos en el directorio raíz del proyecto, agregar el subdirectorio backend
        if (userDir.endsWith("gapsi-ecommerce")) {
            this.DB_FILE_PATH = userDir + "/gapsi-ecommerce-backend/bd.json";
        } else if (userDir.endsWith("gapsi-ecommerce-backend")) {
            this.DB_FILE_PATH = userDir + "/bd.json";
        } else {
            // Fallback: usar ruta relativa
            this.DB_FILE_PATH = "bd.json";
        }

        logger.info("Ruta del archivo bd.json: {}", DB_FILE_PATH);
        logger.info("Directorio de trabajo actual: {}", userDir);
    }

    /**
     * Lee todos los proveedores del archivo JSON
     *
     * @return Lista de proveedores
     */
    public List<Provider> findAll() {
        lock.readLock().lock();
        try {
            File file = new File(DB_FILE_PATH);
            if (!file.exists()) {
                logger.warn("Archivo bd.json no existe. Retornando lista vacía.");
                return new ArrayList<>();
            }

            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<ArrayList<Provider>>() {}.getType();
                List<Provider> providers = gson.fromJson(reader, listType);
                return providers != null ? providers : new ArrayList<>();
            }
        } catch (IOException e) {
            logger.error("Error al leer archivo bd.json", e);
            throw new RuntimeException("Error al leer la base de datos", e);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Busca un proveedor por ID
     *
     * @param id ID del proveedor
     * @return Optional con el proveedor si existe
     */
    public Optional<Provider> findById(Long id) {
        return findAll().stream()
                .filter(provider -> provider.getId().equals(id))
                .findFirst();
    }

    /**
     * Busca un proveedor por nombre (case-insensitive)
     *
     * @param name Nombre del proveedor
     * @return Optional con el proveedor si existe
     */
    public Optional<Provider> findByName(String name) {
        return findAll().stream()
                .filter(provider -> provider.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Verifica si existe un proveedor con el nombre dado
     *
     * @param name Nombre a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existsByName(String name) {
        return findByName(name).isPresent();
    }

    /**
     * Guarda un nuevo proveedor
     *
     * @param provider Proveedor a guardar
     * @return Proveedor guardado con ID asignado
     */
    public Provider save(Provider provider) {
        lock.writeLock().lock();
        try {
            List<Provider> providers = findAll();

            // Asignar ID auto-incremental
            Long newId = providers.stream()
                    .mapToLong(Provider::getId)
                    .max()
                    .orElse(0L) + 1;

            provider.setId(newId);
            providers.add(provider);

            writeToFile(providers);
            logger.info("Proveedor guardado con ID: {}", newId);

            return provider;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Elimina un proveedor por ID
     *
     * @param id ID del proveedor a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean deleteById(Long id) {
        lock.writeLock().lock();
        try {
            List<Provider> providers = findAll();
            boolean removed = providers.removeIf(provider -> provider.getId().equals(id));

            if (removed) {
                writeToFile(providers);
                logger.info("Proveedor con ID {} eliminado", id);
            } else {
                logger.warn("No se encontró proveedor con ID {}", id);
            }

            return removed;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Actualiza un proveedor existente
     *
     * @param provider Proveedor con datos actualizados
     * @return Proveedor actualizado
     */
    public Provider update(Provider provider) {
        lock.writeLock().lock();
        try {
            List<Provider> providers = findAll();

            for (int i = 0; i < providers.size(); i++) {
                if (providers.get(i).getId().equals(provider.getId())) {
                    providers.set(i, provider);
                    writeToFile(providers);
                    logger.info("Proveedor con ID {} actualizado", provider.getId());
                    return provider;
                }
            }

            throw new RuntimeException("Proveedor no encontrado con ID: " + provider.getId());
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Cuenta el total de proveedores
     *
     * @return Total de proveedores
     */
    public long count() {
        return findAll().size();
    }

    /**
     * Escribe la lista de proveedores al archivo JSON
     *
     * @param providers Lista de proveedores
     */
    private void writeToFile(List<Provider> providers) {
        try (FileWriter writer = new FileWriter(DB_FILE_PATH)) {
            gson.toJson(providers, writer);
            writer.flush();
        } catch (IOException e) {
            logger.error("Error al escribir en archivo bd.json", e);
            throw new RuntimeException("Error al escribir en la base de datos", e);
        }
    }
}
