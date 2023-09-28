package com.josedavid.testsonatafy;

/**
 *
 * @author josedavidochoaortiz
 */


public class ListUpdate implements ChangeType {
        private final String property;
        private final Object removed;
        private final Object added;

        public ListUpdate(String property, Object removed, Object added) {
            this.property = property;
            this.removed = removed;
            this.added = added;
        }

        public String getProperty() {
            return property;
        }

        public Object getRemoved() {
            return removed;
        }

        public Object getAdded() {
            return added;
        }
    }