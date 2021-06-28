package ar.gov.mendoza.PrometeoMuni.core.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	boolean isDescription() default false;
	boolean isPrimaryKey() default false;
	boolean isAutoincrement() default false;
	/** Column type. */
	String type() default "TEXT";
	String defaultValue() default "NULL";
	/** Column name. */
	String name();
}