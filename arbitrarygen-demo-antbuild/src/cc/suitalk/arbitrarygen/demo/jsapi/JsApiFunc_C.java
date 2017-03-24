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

package cc.suitalk.arbitrarygen.demo.jsapi;

import cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc;
import cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc;
import cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext;

/**
 * Created by AlbieLiang on 16/11/16.
 */
@JsApiFunc(id = 2, name = "C")
public class JsApiFunc_C extends BaseJsApiFunc {

    @Override
    public boolean invoke(JsApiContext context, Object args, InvokedCallback callback) {
        return false;
    }
}