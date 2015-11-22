package com.soni.messenger.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class AbstractService {
	
	@Autowired
	public SessionFactory sessionFactory;
	
	private Session session;
	
	public Session getCurrentSession() {
		session = sessionFactory.getCurrentSession();
		return session;
	}
	
	public Session getNewSession() {
		session = sessionFactory.openSession();
		return session;
	}
	
	public <E> E getUniqueResult(Query query) {
		E uniqueResult = (E) query.uniqueResult();
		return uniqueResult;
	}
	
	public <E> E getEntity(Class<E> clazz, Serializable pk) {
		session = getCurrentSession();
		E entity = (E) session.get(clazz, pk);
		return entity;
	}
	
	public <E> E updateEntity(E updatedEntity, Serializable pk) {
		Session session = getCurrentSession();
		E staleEntity = (E) session.get(updatedEntity.getClass(), pk);
		
		Field[] fields = updatedEntity.getClass().getDeclaredFields();

		for(int i=0; i<fields.length; i++){
			try {
				if( (!Modifier.isFinal(fields[i].getModifiers()) || Modifier.isStatic(fields[i].getModifiers()) ) 
						&& ( ! fields[i].isAnnotationPresent(OneToMany.class) || fields[i].isAnnotationPresent(ManyToOne.class)) ) {
					fields[i].setAccessible(true);
					Object updatedAuthorFieldValue = fields[i].get(updatedEntity);
					if(updatedAuthorFieldValue != null){
						fields[i].set(staleEntity, updatedAuthorFieldValue);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
		}
		session.update(staleEntity);
		return staleEntity;
	}
}


