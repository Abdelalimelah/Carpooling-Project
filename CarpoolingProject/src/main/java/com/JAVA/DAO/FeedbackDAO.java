package com.JAVA.DAO;

import java.util.List;

import com.JAVA.Beans.FeedbackBean;
import com.JAVA.DAO.Exception.DAOException;

public interface FeedbackDAO {
	
	 public boolean create(FeedbackBean feedback) throws DAOException;
	 public boolean existsByAuthorAndTargetAndTrip(int authorId, int targetId, int tripId) throws DAOException;
	 public double getAverageRatingForTarget(int targetId, String feedbackType) throws DAOException;
	 public List<FeedbackBean> findByTargetAndType(int targetId, String feedbackType) throws DAOException;
	 int countAll() throws DAOException;
	 List<FeedbackBean> findPagedFiltered(String type, Integer authorId, Integer targetId, Integer minRating, int offset,
			int limit) throws DAOException;
	 int countFiltered(String type, Integer authorId, Integer targetId, Integer minRating) throws DAOException;
	 boolean delete(int id) throws DAOException;
}
