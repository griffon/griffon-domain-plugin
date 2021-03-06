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
import griffon.util.GriffonClassUtils;

/**
 * Implements a minimum value constraint.
 *
 * @author Graeme Rocher (Grails 0.4)
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MinConstraint extends AbstractConstraint {
    public static final String VALIDATION_DSL_NAME = "min";
    public static final String DEFAULT_INVALID_MIN_MESSAGE_CODE = "default.invalid.min.message";
    public static final String DEFAULT_INVALID_MIN_MESSAGE = "Property [{0}] of class [{1}] with value [{2}] is less than minimum value [{3}]";

    private Comparable minValue;

    /**
     * @return Returns the minValue.
     */
    public Comparable getMinValue() {
        return minValue;
    }

    /* (non-Javadoc)
     * @see org.codehaus.groovy.grails.validation.Constraint#supports(java.lang.Class)
     */
    public boolean supports(Class type) {
        return type != null && (
            Comparable.class.isAssignableFrom(type) ||
                GriffonClassUtils.isAssignableOrConvertibleFrom(Number.class, type));
    }

    /* (non-Javadoc)
     * @see org.codehaus.groovy.grails.validation.ConstrainedProperty.AbstractConstraint#setParameter(java.lang.Object)
     */
    @Override
    public void setParameter(Object constraintParameter) {
        if (constraintParameter == null) {
            throw new IllegalArgumentException("Parameter for constraint [" +
                VALIDATION_DSL_NAME + "] of property [" +
                constraintPropertyName + "] of class [" + constraintOwningClass + "] cannot be null");
        }

        if (!(constraintParameter instanceof Comparable<?>) && (!constraintParameter.getClass().isPrimitive())) {
            throw new IllegalArgumentException("Parameter for constraint [" +
                VALIDATION_DSL_NAME + "] of property [" +
                constraintPropertyName + "] of class [" + constraintOwningClass +
                "] must implement the interface [java.lang.Comparable]");
        }

        Class<?> propertyClass = GriffonClassUtils.getPropertyType(constraintOwningClass, constraintPropertyName);
        if (!GriffonClassUtils.isAssignableOrConvertibleFrom(constraintParameter.getClass(), propertyClass)) {
            throw new IllegalArgumentException("Parameter for constraint [" + VALIDATION_DSL_NAME +
                "] of property [" + constraintPropertyName + "] of class [" + constraintOwningClass +
                "] must be the same type as property: [" + propertyClass.getName() + "]");
        }

        minValue = (Comparable<?>) constraintParameter;
        super.setParameter(constraintParameter);
    }

    public String getName() {
        return VALIDATION_DSL_NAME;
    }

    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {
        if (null == propertyValue || minValue.compareTo(propertyValue) <= 0) {
            return;
        }

        Object[] args = new Object[]{constraintPropertyName, constraintOwningClass, propertyValue, minValue};
        rejectValue(target, errors, DEFAULT_INVALID_MIN_MESSAGE_CODE,
            VALIDATION_DSL_NAME + NOTMET_SUFFIX, args);
    }
}
