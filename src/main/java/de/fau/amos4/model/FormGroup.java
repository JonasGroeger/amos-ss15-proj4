/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
 * other purposes.
 * Copyright (C) 2015 Attila Bujaki, Werner Sembach, Jonas Gröger, Oswaldo
 *     Bejarano, Ardhi Sutadi, Nikitha Mohan, Benedikt Rauh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created by Yao Bochao on 29/06/2015.
 */

package de.fau.amos4.model;

import java.util.ArrayList;
import java.util.List;

public class FormGroup {
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<FormField> getFields() {
        return fields;
    }
    public void setFields(List<FormField> fields) {
        this.fields = fields;
    }
    String name;
    List<FormField> fields = new ArrayList<FormField>(); 
}
