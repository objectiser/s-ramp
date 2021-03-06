/*
 * Copyright 2012 JBoss Inc
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
package org.overlord.sramp.test;

/**
 * Test utility methods.
 *
 * @author eric.wittmann@redhat.com
 */
public class TestUtils {

	/**
	 * Converts the stream content to a string.
	 * @param is
	 */
	public static String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next(); //$NON-NLS-1$
		} catch (java.util.NoSuchElementException e) {
			return ""; //$NON-NLS-1$
		}
	}

}
