package com.soni.messenger.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AuditableEntity implements Serializable {
	
	@XmlJavaTypeAdapter(DateFormatterAdapter.class) 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	protected Date createdDate;
	
	@XmlJavaTypeAdapter(DateFormatterAdapter.class) 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPD_DATE")
	protected Date lastUpdDate;

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public Date getLastUpdDate() {
		return this.lastUpdDate;
	}

	public static class DateFormatterAdapter extends XmlAdapter<String, Date> {
	      @Override
	      public Date unmarshal(final String v) throws Exception {
	    	  Date date = new Date(new Long(v));
	    	  return date;
	      }

		@Override
		public String marshal(Date v) throws Exception {
			return v.toString();
		}
	}
}
