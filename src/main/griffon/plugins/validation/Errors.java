/*
 * Copyright 2009-2013 the original author or authors.
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
package griffon.plugins.validation;

import griffon.core.Observable;

import java.util.List;

/**
 * @author Andres Almiray
 */
public interface Errors extends Observable {
    String HAS_ERRORS_PROPERTY = "hasErrors";
    String ERROR_COUNT_PROPERTY = "errorCount";

    String getObjectName();

    boolean hasErrors();

    boolean hasGlobalErrors();

    boolean hasFieldErrors();

    boolean hasFieldErrors(String field);

    FieldObjectError getFieldError(String field);

    List<FieldObjectError> getFieldErrors(String field);

    int getFieldErrorCount(String field);

    List<ObjectError> getGlobalErrors();

    int getGlobalErrorCount();

    ObjectError getGlobalError();

    int getErrorCount();

    List<ObjectError> getAllErrors();

    void addError(ObjectError objectError);

    String[] resolveMessageCodes(String code);

    String[] resolveMessageCodes(String code, String field, Class fieldType);

    void reject(String code, Object[] args, String defaultMessage);

    void reject(String code, String defaultMessage);

    void rejectField(String field, String code, Object[] args, String defaultMessage);

    void rejectField(String field, String code, String defaultMessage);

    void rejectField(String field, Object rejectedValue, String code, Object[] args, String defaultMessage);

    void rejectField(String field, Object rejectedValue, String code, String defaultMessage);

    void clearGlobalErrors();

    void clearFieldErrors();

    void clearFieldErrors(String field);

    void clearAllErrors();
}
