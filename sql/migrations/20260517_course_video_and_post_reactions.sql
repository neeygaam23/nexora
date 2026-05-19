USE nexora;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE course_materials ADD COLUMN content_type VARCHAR(100)','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='course_materials' AND column_name='content_type');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @s=(SELECT IF(COUNT(*)=0,'ALTER TABLE course_materials ADD COLUMN media_type VARCHAR(20) DEFAULT ''file''','SELECT 1')
          FROM information_schema.columns
         WHERE table_schema=DATABASE() AND table_name='course_materials' AND column_name='media_type');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

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