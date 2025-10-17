import './CustomFooter.css';

type Props = {
  version?: string;
};

export const CustomFooter = ({ version }: Props) => {
  return (
    <footer className="welcome-footer">
      <span className="version-info">
        {version ? `Versión ${version}` : ''}
      </span>
    </footer>
  );
};

