package com.labelwall.course.entity.base;

import java.io.Serializable;

/**
 * @param <KEY>
 */
public interface Identifier<KEY extends Serializable> {

	public KEY getId(); 
	
}
