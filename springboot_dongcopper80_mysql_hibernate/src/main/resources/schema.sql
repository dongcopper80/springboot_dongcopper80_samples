DROP procedure IF EXISTS `search_user` ^;

CREATE PROCEDURE `search_user` (IN username_in VARCHAR(50))
BEGIN
	SELECT * FROM `sys_user` where `username` = username_in;
END ^;
