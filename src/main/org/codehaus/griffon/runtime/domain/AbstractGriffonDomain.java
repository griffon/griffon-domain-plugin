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

package org.codehaus.griffon.runtime.domain;

import griffon.plugins.domain.GriffonDomain;
import griffon.plugins.domain.GriffonDomainClass;
import griffon.plugins.validation.Errors;
import griffon.plugins.validation.constraints.ConstrainedProperty;
import griffon.plugins.validation.constraints.ConstraintsValidator;
import org.codehaus.griffon.runtime.core.AbstractGriffonArtifact;
import org.codehaus.griffon.runtime.validation.DefaultErrors;

import java.util.List;
import java.util.Map;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.toList;

/**
 * Base implementation of the GriffonDomain interface.
 *
 * @author Andres Almiray
 */
public abstract class AbstractGriffonDomain extends AbstractGriffonArtifact implements GriffonDomain {
    private final Errors errors;

    public AbstractGriffonDomain() {
        this.errors = new DefaultErrors(getClass());
    }

    public boolean validate(String... properties) {
        if (properties == null || properties.length == 0) {
            beforeValidate();
        } else {
            beforeValidate(toList(properties));
        }
        return ConstraintsValidator.evaluate(this, properties);
    }

    public boolean validate(List<String> properties) {
        beforeValidate(properties);
        return ConstraintsValidator.evaluate(this, properties);
    }

    public Errors getErrors() {
        return errors;
    }

    public Map<String, ConstrainedProperty> constrainedProperties() {
        return ((GriffonDomainClass) getGriffonClass()).getConstrainedProperties();
    }

    protected String getArtifactType() {
        return GriffonDomainClass.TYPE;
    }

    public void onLoad() {
    }

    public void onSave() {
    }

    public void beforeLoad() {
    }

    public void beforeInsert() {
    }

    public void beforeUpdate() {
    }

    public void beforeDelete() {
    }

    public void afterLoad() {
    }

    public void afterInsert() {
    }

    public void afterUpdate() {
    }

    public void afterDelete() {
    }

    public void beforeValidate() {
    }

    public void beforeValidate(List<String> propertyNames) {
    }
}