/* Copyright 2004-2005 Graeme Rocher
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
package org.codehaus.griffon.runtime.validation.constraints;

import griffon.plugins.validation.Errors;

import java.lang.reflect.Array;
import java.util.Collection;


/**
 * Validates minimum size or length of the property, for strings and arrays
 * this is the length and collections the size.
 *
 * @author Graeme Rocher (Grails 0.4)
 */
public class MinSizeConstraint extends AbstractConstraint {
    public static final String VALIDATION_DSL_NAME = "minSize";
    public static final String DEFAULT_INVALID_MIN_SIZE_MESSAGE_CODE = "default.invalid.min.size.message";
    public static final String DEFAULT_INVALID_MIN_SIZE_MESSAGE = "Property [{0}] of class [{1}] with value [{2}] is less than the minimum size of [{3}]";

    private int minSize;

    /**
     * @return Returns the minSize.
     */
    public int getMinSize() {
        return minSize;
    }

    /* (non-Javadoc)
     * @see org.codehaus.groovy.grails.validation.ConstrainedProperty.AbstractConstraint#setParameter(java.lang.Object)
     */
    @Override
    public void setParameter(Object constraintParameter) {
        if (!(constraintParameter instanceof Integer)) {
            throw new IllegalArgumentException("Parameter for constraint [" + VALIDATION_DSL_NAME +
                "] of property [" + constraintPropertyName + "] of class [" +
                constraintOwningClass + "] must be a of type [java.lang.Integer]");
        }

        minSize = ((Integer) constraintParameter).intValue();
        super.setParameter(constraintParameter);
    }

    public String getName() {
        return VALIDATION_DSL_NAME;
    }

    /* (non-Javadoc)
     * @see org.codehaus.groovy.grails.validation.Constraint#supports(java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
    public boolean supports(Class type) {
        return type != null && (
            String.class.isAssignableFrom(type) ||
                Collection.class.isAssignableFrom(type) ||
                type.isArray());
    }

    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {
        int length;
        if (propertyValue.getClass().isArray()) {
            length = Array.getLength(propertyValue);
        } else if (propertyValue instanceof Collection<?>) {
            length = ((Collection<?>) propertyValue).size();
        } else { // String
            length = ((String) propertyValue).length();
        }

        if (length < minSize) {
            Object[] args = {constraintPropertyName, constraintOwningClass, propertyValue, minSize};
            rejectValue(target, errors, DEFAULT_INVALID_MIN_SIZE_MESSAGE_CODE,
                VALIDATION_DSL_NAME + NOTMET_SUFFIX, args);
        }
    }
}
