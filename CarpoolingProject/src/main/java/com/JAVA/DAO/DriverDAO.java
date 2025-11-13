package com.JAVA.DAO;

import com.JAVA.Beans.DriverBean;
import com.JAVA.DAO.Exception.DAOException;

public interface DriverDAO {

	 public boolean create(DriverBean driver) throws DAOException;
	 public int getDriverIdByUserId(int userId) throws DAOException;
	 public int findUserIdByDriverId(int driverId) throws DAOException;
}
