CREATE TABLE `tb_note` (
  `id` varchar(36) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `note_type` varchar(20) DEFAULT NULL,
  `file_path` varchar(100) DEFAULT NULL,
  `file_text` text,
  `cata_id` varchar(36) DEFAULT NULL,
  `file_size` varchar(30) DEFAULT NULL,
  `create_oper` varchar(36) DEFAULT NULL,
  `create_date` timestamp DEFAULT now(),
  `update_oper` varchar(36) DEFAULT NULL,
  `update_date` timestamp,
  `state` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_note_learning_task (
  id varchar(100) NOT NULL,
  name varchar(100) NOT NULL,
  note_id varchar(100) NOT NULL,
  plan_time timestamp,
  actual_time timestamp,
  memory_percentage numeric(3),
  create_oper varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  create_date timestamp NOT NULL,
  update_oper varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  update_date timestamp NULL DEFAULT NULL,
  state varchar(5) CHARACTER SET utf8mb4 DEFAULT 'E'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;