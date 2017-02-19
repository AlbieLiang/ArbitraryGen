/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package cc.suitalk.arbitrarygen.utils;

/**
 * 
 * @author AlbieLiang
 *
 */
public final class SignatureCreator {
	
	public static final int TYPE_ANNOTATION = 1;
	public static final String PREFIX = "@SIGNATURE@";
	public static final String PREFIX_ANNOTATION = "@ANNOTATION@";
	public static final String SEPARATOR = "#";

	public static final String create(int type, String...args) {
		StringBuilder builder = new StringBuilder();
		switch (type) {
		case TYPE_ANNOTATION:
			builder.append(PREFIX_ANNOTATION);
			break;

		default:
			builder.append(PREFIX);
			break;
		}
		builder.append(Util.joint(SEPARATOR, args));
		return builder.toString();
	}
}