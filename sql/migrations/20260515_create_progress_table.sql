-- Create progress tracking table
USE nexora;

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
