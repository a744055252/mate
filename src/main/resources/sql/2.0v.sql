CREATE TABLE `mate`.`wx_user`  (
                                   `id` bigint(40) NOT NULL,
                                   `openid` varchar(100) NOT NULL,
                                   `nickname` varchar(255) NULL,
                                   `sex` int(10) NULL,
                                   `city` varchar(255) NULL,
                                   `province` varchar(255) NULL,
                                   `country` varchar(255) NULL,
                                   `head_img_url` varchar(1024) NULL,
                                   `union_id` varbinary(100) NULL,
                                   `privilege_json` varchar(1024) NULL,
                                   `create_time` timestamp(0) NULL,
                                   `update_time` timestamp(0) NULL,
                                   PRIMARY KEY (`id`)
);