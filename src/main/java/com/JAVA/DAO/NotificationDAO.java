package com.JAVA.DAO;

import java.util.List;

import com.JAVA.Beans.NotificationBean;
import com.JAVA.DAO.Exception.DAOException;

public interface NotificationDAO {

	 public int create(NotificationBean notification) throws DAOException;
	 public int countUnread(int userId) throws DAOException;
	 public List<NotificationBean> getUnreadByUser(int userId, int limit, int offset) throws DAOException;
	 public List<NotificationBean> listRecent(int userId, int limit) throws DAOException;
	 public void markAllAsRead(int userId) throws DAOException;
	 public void markAsReadById(int userId, int notificationId) throws DAOException;
	 
}
