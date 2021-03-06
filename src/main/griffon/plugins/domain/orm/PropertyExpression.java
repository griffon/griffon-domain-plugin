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

package griffon.plugins.domain.orm;

/**
 * @author Andres Almiray
 */
public class PropertyExpression implements Criterion {
    private final String propertyName;
    private final Operator operator;
    private final String otherPropertyName;

    public PropertyExpression(String propertyName, Operator operator, String otherPropertyName) {
        this.propertyName = propertyName;
        this.operator = operator;
        this.otherPropertyName = otherPropertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getOtherPropertyName() {
        return otherPropertyName;
    }

    public Operator getOperator() {
        return operator;
    }

    public String toString() {
        return propertyName + " " + operator + " " + otherPropertyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PropertyExpression that = (PropertyExpression) o;

        if (operator != that.operator) return false;
        if (!otherPropertyName.equals(that.otherPropertyName)) return false;
        if (!propertyName.equals(that.propertyName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = propertyName.hashCode();
        result = 31 * result + operator.hashCode();
        result = 31 * result + otherPropertyName.hashCode();
        return result;
    }
}
