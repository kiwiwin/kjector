package org.kiwi.kjector;

import org.kiwi.kjector.container.Container;

public interface InjectPoint {
    Object resolveObject(Container container);
}
