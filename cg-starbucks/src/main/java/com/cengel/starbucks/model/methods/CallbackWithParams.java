package com.cengel.starbucks.model.methods;

@FunctionalInterface
public interface CallbackWithParams {

	Object exec(Object ... objects);

}
