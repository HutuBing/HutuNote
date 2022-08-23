CREATE TABLE `tb_note` (
  `id` varchar(36) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `note_type` varchar(20) DEFAULT NULL,
  `file_path` varchar(100) DEFAULT NULL,
  `file_text` text,
  `cata_id` varchar(36) DEFAULT NULL,
  `file_size` varchar(30) DEFAULT NULL,
  `create_oper` varchar(36) DEFAULT NULL,
  `create_date` timestamp DEFAULT NULL,
  `update_oper` varchar(36) DEFAULT NULL,
  `update_date` timestamp DEFAULT NULL,
  `state` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8