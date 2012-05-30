/*
 * Copyright 2010-2012 the original author or authors.
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
package org.codehaus.griffon.compiler.support;

import griffon.plugins.domain.GriffonDomainProperty;
import griffon.plugins.domain.methods.DefaultPersistentMethods;
import griffon.plugins.domain.methods.MethodSignature;
import org.codehaus.griffon.ast.GriffonASTUtils;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.GStringExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.codehaus.griffon.ast.AbstractASTTransformation.makeClassSafe;
import static org.codehaus.griffon.ast.GriffonASTUtils.implementsOrInheritsZeroArgMethod;
import static org.codehaus.griffon.ast.GriffonASTUtils.injectProperty;

/**
 * Default implementation of domain class injector interface that adds the 'id'
 * and 'version' properties and other previously boilerplate code.
 *
 * @author Andres Almiray
 */
public class DefaultGriffonDomainClassInjector extends GriffonDomainClassInjector {
    private List<ClassNode> classesWithInjectedToString = new ArrayList<ClassNode>();
    private static final ClassNode PROPERTY_TYPE = makeClassSafe(Long.class);

    protected void performInjection(ClassNode classNode) {
        injectIdProperty(classNode);
        injectVersionProperty(classNode);
        injectToStringMethod(classNode, GriffonDomainProperty.IDENTITY);
    }

    protected void injectToStringMethod(ClassNode classNode, String propertyName) {
        final boolean hasToString = implementsOrInheritsZeroArgMethod(
                classNode, "toString", classesWithInjectedToString);

        if (!hasToString && !GriffonASTUtils.isEnum(classNode)) {
            GStringExpression ge = new GStringExpression(classNode.getName() + " : ${" + propertyName + "}");
            ge.addString(new ConstantExpression(classNode.getName() + " : "));
            ge.addValue(new VariableExpression(propertyName));
            Statement s = new ReturnStatement(ge);
            MethodNode mn = new MethodNode("toString", Modifier.PUBLIC, new ClassNode(String.class), new Parameter[0], new ClassNode[0], s);
            classNode.addMethod(mn);
            classesWithInjectedToString.add(classNode);
        }
    }

    protected void injectVersionProperty(ClassNode classNode) {
        injectProperty(classNode, GriffonDomainProperty.VERSION, PROPERTY_TYPE.getTypeClass(), null);
    }

    protected void injectIdProperty(ClassNode classNode) {
        injectProperty(classNode, GriffonDomainProperty.IDENTITY, PROPERTY_TYPE.getTypeClass(), null);
    }

    @Override
    protected MethodSignature[] getProvidedMethods() {
        Set<MethodSignature> methodSignatures = new TreeSet<MethodSignature>();
        for (DefaultPersistentMethods method : DefaultPersistentMethods.values()) {
            MethodSignature[] signatures = method.getMethodSignatures();
            for (int i = 0, length = signatures.length; i < length; i++) {
                methodSignatures.add(signatures[i]);
            }
        }
        return methodSignatures.toArray(new MethodSignature[methodSignatures.size()]);
    }
}
