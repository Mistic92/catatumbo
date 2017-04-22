/*
 * Copyright 2017 Sai Pullabhotla.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jmethods.catatumbo.mappers;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.TimestampValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MappingException;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * An implementation of {@link Mapper} for mapping {@link OffsetDateTime}
 * to/from Cloud Datastore. {@link OffsetDateTime} types are mapped to DateTime
 * type in the Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class OffsetDateTimeMapper implements Mapper {

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		OffsetDateTime offsetDateTime = (OffsetDateTime) input;
		Date date = Date.from(offsetDateTime.toInstant());
		return TimestampValue.newBuilder(Timestamp.of(date));
	}

	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		try {
			TimestampValue dateTimeValue = (TimestampValue) input;
			Timestamp dateTime = dateTimeValue.get();
			Date date = new Date(dateTime.getNanos()*1000);
			return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		} catch (ClassCastException exp) {
			String pattern = "Expecting %s, but found %s";
			throw new MappingException(
					String.format(pattern, TimestampValue.class.getName(), input.getClass().getName()), exp);
		}
	}

}
