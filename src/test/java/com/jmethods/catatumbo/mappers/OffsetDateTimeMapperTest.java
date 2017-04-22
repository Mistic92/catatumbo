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

import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.TimestampValue;
import com.jmethods.catatumbo.MappingException;
import org.junit.Test;

import java.time.OffsetDateTime;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Sai Pullabhotla
 *
 */
public class OffsetDateTimeMapperTest {

	@Test
	public void testToDatastore_Now() {
		OffsetDateTimeMapper mapper = new OffsetDateTimeMapper();
		OffsetDateTime now = OffsetDateTime.now();
		TimestampValue v = (TimestampValue) mapper.toDatastore(now).build();
		assertTrue(now.toInstant().toEpochMilli() == v.get().getNanos()*1000);
	}

	@Test
	public void testToDatastore_Null() {
		OffsetDateTimeMapper mapper = new OffsetDateTimeMapper();
		NullValue v = (NullValue) mapper.toDatastore(null).build();
		assertNull(v.get());
	}
	
	@Test
	public void testToModel_Null() {
		OffsetDateTimeMapper mapper = new OffsetDateTimeMapper();
		OffsetDateTime output = (OffsetDateTime) mapper.toModel(NullValue.of());
		assertNull(output);
	}

	@Test(expected = MappingException.class)
	public void testToModel2() {
		StringValue v = StringValue.of("Hello");
		OffsetDateTimeMapper mapper = new OffsetDateTimeMapper();
		try {
			mapper.toModel(v);
		} catch (MappingException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
