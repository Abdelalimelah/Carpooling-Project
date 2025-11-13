package com.JAVA.DAO;

import com.JAVA.Beans.UserBean;
import com.JAVA.DAO.Exception.DAOException;

public interface UserDAO {

	public boolean register(UserBean user) throws DAOException;
	public boolean emailExists(String email) throws DAOException;
	public UserBean findByEmail(String email) throws DAOException;
	public UserBean findById(int id) throws DAOException;
	public boolean update(UserBean user) throws DAOException;
	public boolean updatePassword(int userId, String hashedPassword) throws DAOException;
}
