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

package cc.suitalk.arbitrarygen.extension.processoing;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by AlbieLiang on 2017/4/9.
 */

public abstract class AbstractAGAnnotationProcessor implements AGAnnotationProcessor {

    /**
     * If the processor class is annotated with {@link
     * AGSupportedAnnotationTypes}, return an unmodifiable set with the
     * same set of strings as the annotation.  If the class is not so
     * annotated, an empty set is returned.
     *
     * @return the names of the annotation types supported by this
     * processor, or an empty set if none
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        AGSupportedAnnotationTypes sat = this.getClass().getAnnotation(AGSupportedAnnotationTypes.class);
        if (sat == null) {
            return Collections.emptySet();
        } else {
            return arrayToSet(sat.value());
        }
    }

    private static Set<String> arrayToSet(String[] array) {
        assert array != null;
        Set<String> set = new HashSet<>(array.length);
        for (String s : array) {
            set.add(s);
        }
        return Collections.unmodifiableSet(set);
    }
}
