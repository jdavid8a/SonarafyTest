package com.josedavid.testsonatafy;

/**
 *
 * @author josedavidochoaortiz
 */

public class PropertyUpdate implements ChangeType {
        private final String property;
        private final Object previous;
        private final Object current;

        public PropertyUpdate(String property, Object previous, Object current) {
            this.property = property;
            this.previous = previous;
            this.current = current;
        }

        public String getProperty() {
            return property;
        }

        public Object getPrevious() {
            return previous;
        }

        public Object getCurrent() {
            return current;
        }
}