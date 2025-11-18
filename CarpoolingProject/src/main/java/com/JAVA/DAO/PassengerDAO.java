package com.JAVA.DAO;

import java.util.List;

import com.JAVA.Beans.PassengerBean;
import com.JAVA.DAO.Exception.DAOException;

public interface PassengerDAO {
	
	 public boolean create(PassengerBean car) throws DAOException;
	 public int findUserIdByPassengerId(int passengerId) throws DAOException;
	 public int findByUserId(int userId) throws DAOException;
	 int countAll() throws DAOException;
	 List<PassengerBean> findAll() throws DAOException;
	 PassengerBean findById(int id) throws DAOException;
	 boolean deleteByUserId(int userId) throws DAOException;
}
