-- Add missing profile, community, course, and enrollment columns
USE nexora;

ALTER TABLE users
  ADD COLUMN profile_picture VARCHAR(255),
  ADD COLUMN bio TEXT,
  ADD COLUMN is_active BOOLEAN DEFAULT TRUE;

ALTER TABLE communities
  ADD COLUMN category VARCHAR(100),
  ADD COLUMN banner_image VARCHAR(255),
  ADD COLUMN is_private BOOLEAN DEFAULT FALSE;

ALTER TABLE courses
  ADD COLUMN is_paid BOOLEAN DEFAULT FALSE,
  ADD COLUMN price DECIMAL(10,2) DEFAULT 0.00;

ALTER TABLE enrollments
  ADD COLUMN payment_status ENUM('free','paid') DEFAULT 'free',
  ADD COLUMN amount_paid DECIMAL(10,2) DEFAULT 0.00;
