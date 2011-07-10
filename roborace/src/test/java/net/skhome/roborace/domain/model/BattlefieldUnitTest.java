/*
 * Copyright 2011 the original author or authors.
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
package net.skhome.roborace.domain.model;

import java.util.List;

import net.skhome.roborace.MockitoTestCase;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Implements unit tests for {@link Battlefield}
 *
 * @author Sascha Krüger (sascha@skhome.net)
 */
public class BattlefieldUnitTest extends MockitoTestCase {

	@Mock
	private BattlefieldMap battlefieldMap;

	@Test
	public void shouldReturnMapFromConstructor() throws Exception {

		// given
		final Battlefield battlefield = new Battlefield(battlefieldMap);

		// when
		final BattlefieldMap result = battlefield.getMap();

		// then
		assertThat(result, is(sameInstance(battlefieldMap)));

	}

	@Test
	public void shouldNeverReturnNullPieces() throws Exception {

		// given
		final Battlefield battlefield = new Battlefield(battlefieldMap);

		// when
		final List<Robot> pieces = battlefield.getPieces();

		// then
		assertThat(pieces, is(notNullValue()));

	}
	
}
