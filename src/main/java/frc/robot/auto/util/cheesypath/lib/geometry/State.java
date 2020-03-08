package frc.robot.auto.util.cheesypath.lib.geometry;

import frc.robot.auto.util.cheesypath.lib.util.CSVWritable;
import frc.robot.auto.util.cheesypath.lib.util.Interpolable;

public interface State<S> extends Interpolable<S>, CSVWritable {
    double distance(final S other);

    boolean equals(final Object other);

    String toString();

    String toCSV();
}
