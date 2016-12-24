/*
 * Copyright 2016 DELTIS Engineering sprl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.deltis.documentarchiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;

/**
 * Created by benoit on 17/03/14.
 */
public class AccountingDocs {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String... args) {
        new AccountingDocs().start();
    }

    private void start() {
        try {
            watch();
        } catch (IOException ioe) {
            logger.error("Failed to start watching", ioe);
        }
    }

    private void watch() throws IOException {
    }
}