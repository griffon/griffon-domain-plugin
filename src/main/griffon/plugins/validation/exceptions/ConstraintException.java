/*
 * Copyright 2004-2013 the original author or authors.
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
package griffon.plugins.validation.exceptions;

import griffon.exceptions.GriffonException;

/**
 * @author Andres Almiray
 */
public class ConstraintException extends GriffonException {
    public ConstraintException() {
    }

    public ConstraintException(String arg0) {
        super(arg0);
    }

    public ConstraintException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ConstraintException(Throwable arg0) {
        super(arg0);
    }
}
