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
package org.codehaus.griffon.runtime.domain.methods;

import griffon.plugins.domain.GriffonDomainClass;
import griffon.plugins.domain.GriffonDomainHandler;
import griffon.plugins.domain.exceptions.UnsupportedDomainMethodException;
import griffon.plugins.domain.methods.FindAllByMethod;
import griffon.plugins.domain.orm.Criterion;
import griffon.plugins.domain.orm.Restrictions;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Andres Almiray
 */
public abstract class AbstractFindAllByPersistentMethod extends AbstractClausedStaticPersistentMethod implements FindAllByMethod {
    private static final String METHOD_PATTERN = "(" + METHOD_NAME + ")([A-Z]\\w*)";

    public AbstractFindAllByPersistentMethod(GriffonDomainHandler griffonDomainHandler) {
        super(griffonDomainHandler, Pattern.compile(METHOD_PATTERN), OPERATORS);
    }

    @Override
    protected Object doInvokeInternalWithExpressions(GriffonDomainClass domainClass, String methodName, Object[] arguments, List<GriffonMethodExpression> expressions, String operatorInUse) {
        Map<String, Object> options = Collections.emptyMap();

        if (arguments.length == 1 && arguments[0] instanceof Map) {
            options = (Map) arguments[0];
        }

        final String operator = OPERATOR_OR.equals(operatorInUse) ? OPERATOR_OR : OPERATOR_AND;
        if (expressions.size() == 1) {
            return findAllBy(domainClass, methodName, expressions.get(0).getCriterion(), options);
        } else {
            List<Criterion> criteria = new ArrayList<Criterion>();
            for (GriffonMethodExpression expr : expressions) {
                criteria.add(expr.getCriterion());
            }
            Criterion[] array = criteria.toArray(new Criterion[criteria.size()]);
            Criterion criterion = operator.equals(OPERATOR_OR) ? Restrictions.or(array) : Restrictions.and(array);
            return findAllBy(domainClass, methodName, criterion, options);
        }
    }

    protected Collection findAllBy(GriffonDomainClass domainClass, String methodName, Criterion criterion, Map<String, Object> options) {
        throw new UnsupportedDomainMethodException();
    }
}