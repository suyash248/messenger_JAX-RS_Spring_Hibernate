package com.soni.messenger.model.interceptor;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import com.soni.messenger.model.AuditableEntity;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class HibernateInterceptor extends EmptyInterceptor{
	
	private static final long serialVersionUID = 1L;
	
	// This method is called when entity gets created.
	public boolean onSave(Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
		AuditableEntity auditableEntity = this.getAuditableEntity(entity);
		if(auditableEntity!= null) {
			for(int i=0; i<propertyNames.length; i++){
				if("createdDate".equalsIgnoreCase(propertyNames[i])) {
					state[i] = new Date();
					return true;
				}
			}
		}
		return false;
	}
	
	// This method is called when entity gets updated.
	public boolean onFlushDirty(Object entity,
	                Serializable id,
	                Object[] currentState,
	                Object[] previousState,
	                String[] propertyNames,
	                Type[] types) {
	     AuditableEntity auditableEntity = this.getAuditableEntity(entity);
	     if(auditableEntity!= null) {
			for(int i=0; i<propertyNames.length; i++){
				if("lastUpdDate".equalsIgnoreCase(propertyNames[i])) {
					currentState[i] = new Date();
					return true;
				}
			}
		}
	    return false;
	}
	
	public boolean onLoad(Object entity,
	               Serializable id,
	               Object[] state,
	               String[] propertyNames,
	               Type[] types) {
	  // do nothing
	  return true;
	}
	
	public void onDelete(Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
	// do nothing
	}
	
	//called before commit into database
	public void preFlush(Iterator iterator) {
		// do nothing
	}
	
	//called after committed into database
	public void postFlush(Iterator iterator) {
		// do nothing
	}
	
	/**
	 * @param entity
	 * @return An instance of {@link AuditableEntity}
	 */
	private AuditableEntity getAuditableEntity(Object entity) {
		if(entity instanceof AuditableEntity)
			return (AuditableEntity)entity;
		return null;
	}
}
