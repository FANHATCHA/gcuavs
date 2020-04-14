-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 14, 2020 at 01:04 PM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gcuavs`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` int(10) UNSIGNED NOT NULL,
  `ticket_number` int(225) UNSIGNED DEFAULT NULL,
  `comment` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(10) UNSIGNED DEFAULT NULL,
  `created_at` varchar(225) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `ticket_number`, `comment`, `user_id`, `created_at`) VALUES
(9, 2108907711, 'This is the first comment !', 27, '13-04-2020 11:52:59'),
(10, 2108907711, 'This is the first comment !', 27, '13-04-2020 11:53:03'),
(11, 1017986267, 'This is my first comment', 27, '13-04-2020 11:54:59'),
(12, 1017986267, 'This is my first comment', 27, '13-04-2020 11:55:03'),
(13, 2108907711, 'ok coool', 27, '13-04-2020 11:57:58'),
(14, 2108907711, 'fff', 27, '13-04-2020 13:28:34'),
(15, 2108907711, 'fff', 27, '13-04-2020 13:28:39'),
(16, 63458022, 'This is a test', 27, '13-04-2020 17:59:29'),
(17, 63458022, 'This is a test', 27, '13-04-2020 17:59:36'),
(18, 63458022, 'ssss', 27, '13-04-2020 21:03:21'),
(19, 63458022, 'test', 27, '13-04-2020 21:04:06'),
(20, 63458022, 'ttt', 27, '13-04-2020 21:07:58'),
(21, 2108907711, 'This is a comment', 27, '13-04-2020 21:31:34'),
(22, 2108907711, 'This is a comment', 27, '13-04-2020 21:31:41'),
(23, 2108907711, 'This is a comment', 27, '13-04-2020 21:32:54'),
(24, 2108907711, 'This is a comment', 27, '13-04-2020 21:32:55'),
(25, 885567717, 'This is a comment', 27, '13-04-2020 22:18:54'),
(26, 885567717, 'This is a comment', 27, '13-04-2020 22:18:57'),
(27, 885567717, 'This is a comment', 27, '13-04-2020 22:30:59'),
(28, 885567717, 'This is a comment', 27, '13-04-2020 22:31:02');

-- --------------------------------------------------------

--
-- Table structure for table `priorities`
--

CREATE TABLE `priorities` (
  `id` int(10) UNSIGNED NOT NULL,
  `ticket_number` int(225) UNSIGNED DEFAULT NULL,
  `priority` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `priorities`
--

INSERT INTO `priorities` (`id`, `ticket_number`, `priority`) VALUES
(1, 1017986267, 'MEDIUM'),
(2, 2108907711, 'MEDIUM'),
(3, 885567717, 'LOW'),
(4, 63458022, 'MEDIUM');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `display_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`, `display_name`, `created_at`, `updated_at`) VALUES
(0, 'developer', 'Developer', '2018-07-20 06:05:20', '2018-07-20 06:05:20'),
(1, 'staff_admin', 'Staff Admin', '2018-07-20 06:05:20', '2018-07-20 06:05:20'),
(2, 'gcuavs_staff', 'GCUAVS staff', '2020-03-10 20:00:00', '2020-03-10 20:00:00'),
(3, 'academic_staff', 'Academic staff', '2020-03-10 20:00:00', '2020-03-10 20:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `id` int(10) UNSIGNED NOT NULL,
  `ticket_number` int(225) UNSIGNED DEFAULT NULL,
  `progress` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`id`, `ticket_number`, `progress`, `user_id`) VALUES
(10, 63458022, 'Closed', 27),
(12, 2108907711, 'Open', 27),
(13, 885567717, 'In-progress', 27);

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

CREATE TABLE `tickets` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `number` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `requester_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `assigned_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`id`, `number`, `name`, `description`, `requester_id`, `assigned_id`, `created_at`) VALUES
(4, 2108907711, 'ss', 'ss', '26', '30', '06-04-2020 15:03:54'),
(6, 63458022, 'Wooden MIC', 'I want a wooden MIC for a special presentation', '26', '27', '13-04-2020 16:44:28'),
(7, 885567717, 'Wooden MIC', 'I want a wooden MIC for a special presentation', '26', '27', '13-04-2020 16:44:34');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL,
  `role_id` int(10) UNSIGNED DEFAULT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `role_id`, `name`, `email`, `password`) VALUES
(5, 0, 'Kone Fanhatcha', 'kfanhatcha16@alustudent.com', '$2a$12$zw4yDctB3sAp10AUGLFBPebs986WWseSsS5B2QemFaDJRirHHbsQG'),
(16, 1, 'John Doe', 'john.doe@gcu.ac.uk', '$2a$12$K3bfYkLLeg60KFUOWylS1e41ZOu7CcBvy3gzIn4lTihLjJq..vGYm'),
(26, 3, 'Tim cook', 'tim.cook@gcu.ac.uk', '$2a$12$TcPjGLNaRt6mIg4nq4fNNO71zZLMfVaJxk0mQomPCSYWMr.kI/nhC'),
(27, 2, 'Steve Bernard', 'steve@gcu.ac.uk', '$2a$12$KUC2WfAs/M.NOL37eNlPcuJivIyrcteqg/UrEffyyr54.iWueVy7W'),
(29, 3, 'Test Academic Staff', 'test.academicstaff@gcu.ac.uk', '$2a$12$cECBc7dhH.pHe35eAmUB2uK4rFYxpkbNOPGWe24HxQzP5l2sOoOku'),
(30, 2, 'Test AVS STAFF', 'test.avsstaff@gcu.ac.uk', '$2a$12$WMab7OpVM4apb1Xa/pvAY.9xV1jlv4HizMlEiHrCFhjeUgikJTPZ.');

-- --------------------------------------------------------

--
-- Table structure for table `user_attempts`
--

CREATE TABLE `user_attempts` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `ip_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `attempts` bigint(20) UNSIGNED NOT NULL,
  `last_modified` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_attempts`
--

INSERT INTO `user_attempts` (`id`, `ip_address`, `email`, `attempts`, `last_modified`) VALUES
(1, '0:0:0:0:0:0:0:1', 'kfanhatcha16@alustudent.com', 4, '57/14/2020');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `comments_ticket_number_foreign` (`ticket_number`),
  ADD KEY `comments_user_id_foreign` (`user_id`);

--
-- Indexes for table `priorities`
--
ALTER TABLE `priorities`
  ADD PRIMARY KEY (`id`),
  ADD KEY `priorities_ticket_number_foreign` (`ticket_number`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `roles_name_unique` (`name`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `status_ticket_number_foreign` (`ticket_number`),
  ADD KEY `status_user_id_foreign` (`user_id`);

--
-- Indexes for table `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tickets_requester_id_foreign` (`requester_id`),
  ADD KEY `tickets_assigned_id_foreign` (`assigned_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`),
  ADD KEY `users_role_id_foreign` (`role_id`);

--
-- Indexes for table `user_attempts`
--
ALTER TABLE `user_attempts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_attempts_email_foreign` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `priorities`
--
ALTER TABLE `priorities`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `tickets`
--
ALTER TABLE `tickets`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `user_attempts`
--
ALTER TABLE `user_attempts`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_role_id_foreign` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Constraints for table `user_attempts`
--
ALTER TABLE `user_attempts`
  ADD CONSTRAINT `user_attempts_email_foreign` FOREIGN KEY (`email`) REFERENCES `users` (`email`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
