package io.swagger.configuration;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.time.format.DateTimeFormatter;

public abstract class DateTimeDeserializerBase<T> extends StdScalarDeserializer<T> {
  protected final DateTimeFormatter _formatter;

  protected DateTimeDeserializerBase(Class<T> vc, DateTimeFormatter formatter) {
    super(vc);
    _formatter = formatter;
  }

  protected abstract JsonDeserializer<T> withDateFormat(DateTimeFormatter dtf);
}
