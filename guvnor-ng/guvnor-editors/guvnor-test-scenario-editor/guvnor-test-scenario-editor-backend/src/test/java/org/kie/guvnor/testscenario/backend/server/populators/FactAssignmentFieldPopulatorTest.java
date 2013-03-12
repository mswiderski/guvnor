/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.guvnor.testscenario.backend.server.populators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.HashSet;

import org.drools.base.ClassTypeResolver;
import org.drools.base.TypeResolver;
import org.junit.Test;
import org.kie.guvnor.testscenario.backend.server.MatryoshkaDoll;
import org.kie.guvnor.testscenario.backend.server.Mouse;
import org.drools.guvnor.models.testscenarios.shared.FactAssignmentField;
import org.drools.guvnor.models.testscenarios.shared.FieldData;

public class FactAssignmentFieldPopulatorTest {

    @Test
    public void testFactAssignmentField() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        TypeResolver typeResolver = new ClassTypeResolver(new HashSet<String>(),classLoader );
        typeResolver.addImport("org.kie.guvnor.testscenario.backend.server.Cheese");

        Mouse mouse = new Mouse();

        FactAssignmentField factAssignmentField = new FactAssignmentField("cheese", "Cheese");

        FactAssignmentFieldPopulator factAssignmentFieldPopulator = new FactAssignmentFieldPopulator(mouse, factAssignmentField, typeResolver, classLoader);

        factAssignmentFieldPopulator.populate(new HashMap<String, Object>());

        assertNotNull(mouse.getCheese());
    }

    @Test
    public void testSimpleFields() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        TypeResolver typeResolver = new ClassTypeResolver(new HashSet<String>(),classLoader );
        typeResolver.addImport("org.kie.guvnor.testscenario.backend.server.Cheese");

        Mouse mouse = new Mouse();

        FactAssignmentField factAssignmentField = new FactAssignmentField("cheese", "Cheese");
        factAssignmentField.getFact().getFieldData().add(new FieldData("type", "Best cheddar EVER! (tm)"));

        FactAssignmentFieldPopulator factAssignmentFieldPopulator = new FactAssignmentFieldPopulator(mouse, factAssignmentField, typeResolver, classLoader);

        factAssignmentFieldPopulator.populate(new HashMap<String, Object>());

        assertEquals("Best cheddar EVER! (tm)", mouse.getCheese().getType());
    }

    @Test
    public void testMatryoshkaDollSituation() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        TypeResolver typeResolver = new ClassTypeResolver(new HashSet<String>(),classLoader );
        typeResolver.addImport("org.kie.guvnor.testscenario.backend.server.MatryoshkaDoll");

        MatryoshkaDoll matryoshkaDoll = new MatryoshkaDoll();

        FactAssignmentField factAssignmentField = createFactAssignmentField();
        addFactAssignmentFieldIntoFactAssignmentField(factAssignmentField, 5);

        FactAssignmentFieldPopulator factAssignmentFieldPopulator = new FactAssignmentFieldPopulator(matryoshkaDoll, factAssignmentField, typeResolver, classLoader);

        factAssignmentFieldPopulator.populate(new HashMap<String, Object>());

        assertNotNull(matryoshkaDoll.getMatryoshkaDoll());
        assertNotNull(matryoshkaDoll.getMatryoshkaDoll().getMatryoshkaDoll());
        assertNotNull(matryoshkaDoll.getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll());
        assertNotNull(matryoshkaDoll.getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll());
        assertNotNull(matryoshkaDoll.getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll());
        assertNotNull(matryoshkaDoll.getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll());
        assertNull(matryoshkaDoll.getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll().getMatryoshkaDoll());
    }

    private void addFactAssignmentFieldIntoFactAssignmentField(FactAssignmentField factAssignmentField, int times) {
        if (times > 0) {
            FactAssignmentField innerFactAssignmentField = createFactAssignmentField();

            factAssignmentField.getFact().getFieldData().add(innerFactAssignmentField);

            addFactAssignmentFieldIntoFactAssignmentField(innerFactAssignmentField, --times);
        }
    }

    private FactAssignmentField createFactAssignmentField() {
        return new FactAssignmentField("matryoshkaDoll", "MatryoshkaDoll");
    }
}
