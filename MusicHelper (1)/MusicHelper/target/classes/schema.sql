-- Executado automaticamente pela Conexao.java a cada conexão; por isso usa IF NOT EXISTS.

CREATE TABLE IF NOT EXISTS usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  senha VARCHAR(255) NOT NULL,
  instrumento VARCHAR(50) DEFAULT 'Guitarra',
  experiencia VARCHAR(50) DEFAULT '5 anos',
  biografia TEXT,
  data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  banda VARCHAR(255),
  repertorio TEXT,
  instrumentos VARCHAR(255),
  foto_perfil VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS compromissos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  titulo VARCHAR(255) NOT NULL,
  tipo VARCHAR(50) NOT NULL,
  data_compromisso DATE NOT NULL,
  hora_inicio TIME NOT NULL,
  hora_fim TIME NOT NULL,
  local_compromisso VARCHAR(255) NOT NULL,
  participantes INT DEFAULT 1,
  criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  notificacao_vista TINYINT DEFAULT 0,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS historico (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  titulo VARCHAR(255) NOT NULL,
  descricao TEXT NOT NULL,
  categoria VARCHAR(50) NOT NULL,
  data_atividade TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS musicas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  titulo VARCHAR(255) NOT NULL,
  artista VARCHAR(255) NOT NULL,
  tom VARCHAR(10),
  tipo VARCHAR(50) NOT NULL,
  link_externo VARCHAR(500),
  anotacoes TEXT,
  arquivo_path VARCHAR(500),
  criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  titulo VARCHAR(150) NOT NULL,
  conteudo TEXT NOT NULL,
  categoria VARCHAR(50) NOT NULL,
  data_criacao DATE NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);
