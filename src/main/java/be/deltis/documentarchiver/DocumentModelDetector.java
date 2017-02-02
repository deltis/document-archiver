/*
 *  Copyright 2016-2017 DELTIS Engineering sprl
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package be.deltis.documentarchiver;

import be.deltis.documentarchiver.context.Context;
import be.deltis.documentarchiver.model.DocumentModel;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by benoit on 29/12/16 - 16:44.
 */
public interface DocumentModelDetector {

    List<DocumentModel> searchModels(Path filename, Context context);
}
