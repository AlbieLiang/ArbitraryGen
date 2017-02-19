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

package cc.suitalk.arbitrarygen.rule;

/**
 * Created by AlbieLiang on 2016/11/26.
 */
public class Rule {

    public static final int TYPE_RULE = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_DIRECTORY = 2;
    public static final int TYPE_RECURSION_DIRECTORY = 3;

    private int type;

    private String content;

    public Rule() {
    }

    public Rule(String content) {
        setContent(content);
    }

    public Rule(int type, String content) {
        setType(type);
        setContent(content);
    }

    public int getType() {
        return type;
    }

    public Rule setType(int type) {
        this.type = type;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Rule setContent(String content) {
        this.content = content;
        return this;
    }
}
