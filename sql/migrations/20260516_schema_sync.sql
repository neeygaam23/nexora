-- Idempotent schema sync for local/dev databases
USE nexora;

CREATE TABLE IF NOT EXISTS roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL UNIQUE
);

INSERT IGNORE INTO roles (name) VALUES ('ADMIN'), ('CREATOR'), ('MEMBER');

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role_id INT NOT NULL,
  full_name VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS communities (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  description TEXT,
  creator_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS courses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  community_id INT NOT NULL,
  title VARCHAR(200) NOT NULL,
  description TEXT,
  creator_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (community_id) REFERENCES communities(id) ON DELETE CASCADE,
  FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS enrollments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  course_id INT NOT NULL,
  user_id INT NOT NULL,
  enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uq_enroll (course_id, user_id),
  FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS posts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  community_id INT NULL,
  course_id INT NULL,
  author_id INT NOT NULL,
  title VARCHAR(200),
  body TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  post_id INT NOT NULL,
  author_id INT NOT NULL,
  parent_comment_id INT NULL,
  body TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
  FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (parent_comment_id) REFERENCES comments(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS community_members (
  id INT AUTO_INCREMENT PRIMARY KEY,
  community_id INT NOT NULL,
  user_id INT NOT NULL,
  joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uq_community_member (community_id, user_id),
  FOREIGN KEY (community_id) REFERENCES communities(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_follows (
  id INT AUTO_INCREMENT PRIMARY KEY,
  follower_id INT NOT NULL,
  followed_id INT NOT NULL,
  followed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uq_follow (follower_id, followed_id),
  FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (followed_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS course_materials (
  id INT AUTO_INCREMENT PRIMARY KEY,
  course_id INT NOT NULL,
  uploader_id INT NULL,
  filename VARCHAR(255) NOT NULL,
  stored_path VARCHAR(1024) NOT NULL,
  content_type VARCHAR(100),
  media_type VARCHAR(20) DEFAULT 'file',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
  FOREIGN KEY (uploader_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS post_reactions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  post_id INT NOT NULL,
  user_id INT NOT NULL,
  reaction_type VARCHAR(20) NOT NULL DEFAULT 'LIKE',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uq_post_reaction (post_id, user_id, reaction_type),
  FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS progress (
  progress_id INT AUTO_INCREMENT PRIMARY KEY,
  enrollment_id INT NOT NULL,
  module_id INT NOT NULL,
  is_completed BOOLEAN DEFAULT FALSE,
  completed_at TIMESTAMP NULL,
  UNIQUE KEY uq_progress_enrollment_module (enrollment_id, module_id),
  CONSTRAINT fk_progress_enrollment
    FOREIGN KEY (enrollment_id) REFERENCES enrollments(id) ON DELETE CASCADE
);

-- Additive columns used by DAO/model code (guarded through information_schema)
SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE users ADD COLUMN profile_picture VARCHAR(255)','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='users' AND column_name='profile_picture');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE users ADD COLUMN bio TEXT','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='users' AND column_name='bio');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE users ADD COLUMN is_active BOOLEAN DEFAULT TRUE','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='users' AND column_name='is_active');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE communities ADD COLUMN category VARCHAR(100)','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='communities' AND column_name='category');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE communities ADD COLUMN banner_image VARCHAR(255)','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='communities' AND column_name='banner_image');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE communities ADD COLUMN is_private BOOLEAN DEFAULT FALSE','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='communities' AND column_name='is_private');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE courses ADD COLUMN is_paid BOOLEAN DEFAULT FALSE','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='courses' AND column_name='is_paid');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE courses ADD COLUMN price DECIMAL(10,2) DEFAULT 0.00','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='courses' AND column_name='price');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE enrollments ADD COLUMN payment_status ENUM(\'free\',\'paid\') DEFAULT \'free\'','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='enrollments' AND column_name='payment_status');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE enrollments ADD COLUMN amount_paid DECIMAL(10,2) DEFAULT 0.00','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='enrollments' AND column_name='amount_paid');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;
