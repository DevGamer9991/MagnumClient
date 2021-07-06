/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2021 Meteor Development.
 */

package net.geeky.magnumclient.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;

public class BoundingBox implements ISerializable<BoundingBox> {
    public AlignmentX x = AlignmentX.Left;
    public AlignmentY y = AlignmentY.Top;

    MinecraftClient client = MinecraftClient.getInstance();

    public double xOffset, yOffset;
    public double width, height;

    public double alignX(double width) {
        switch (this.x) {
            default:     return 0;
            case Center: return this.width / 2.0 - width / 2.0;
            case Right:  return this.width - width;
        }
    }

    public void setX(int x) {
        addPos(x - getX(), 0);
    }

    public void setY(int y) {
        addPos(0, y - getY());
    }

    public void addPos(double deltaX, double deltaY) {
        xOffset += (deltaX);
        yOffset += (deltaY);

        double xPos = getX();
        double yPos = getY();

        // X
        switch (x) {
            case Left: {
                double c = client.getWindow().getScaledWidth() / 3.0;

                if (xPos >= c - width / 2.0) {
                    // Module is closer to center than left
                    x = AlignmentX.Center;
                    xOffset = (-c / 2.0 + xPos - c + width / 2.0);
                }

                break;
            }
            case Center: {
                double c = client.getWindow().getScaledWidth() / 3.0;
                double cRight = client.getWindow().getScaledWidth() / 3.0 * 2;

                if (xPos > cRight - width / 2.0) {
                    // Module is closer to right than center
                    x = AlignmentX.Right;
                    xOffset = (-(c - width) + (c - (client.getWindow().getScaledWidth() - xPos)));
                } else if (xPos < c - width / 2.0) {
                    // Module is closer to left than center
                    x = AlignmentX.Left;
                    xOffset = (xPos);
                }

                break;
            }
            case Right: {
                double c = client.getWindow().getScaledWidth() / 3.0;
                double cLeft = client.getWindow().getScaledWidth() / 3.0 * 2;

                if (xPos <= cLeft - width / 2.0) {
                    // Module is closer to center than right
                    x = AlignmentX.Center;
                    xOffset = (-c / 2.0 + xPos - c + width / 2.0);
                }

                break;
            }
        }

        if (x == AlignmentX.Left && xOffset < 0) xOffset = 0;
        else if (x == AlignmentX.Right && xOffset > 0) xOffset = 0;

        // Y
        switch (y) {
            case Top: {
                double c = client.getWindow().getScaledHeight() / 3.0;

                if (yPos >= c - height / 2.0) {
                    // Module is closer to center than top
                    y = AlignmentY.Center;
                    yOffset = (-c / 2.0 + yPos - c + height / 2.0);
                }

                break;
            }
            case Center: {
                double c = client.getWindow().getScaledHeight() / 3.0;
                double cBottom = client.getWindow().getScaledHeight() / 3.0 * 2;

                if (yPos > cBottom - height / 2.0) {
                    // Module is closer to bottom than center
                    y = AlignmentY.Bottom;
                    yOffset = (-(c - height) + (c - (client.getWindow().getScaledHeight() - yPos)));
                } else if (yPos < c - height / 2.0) {
                    // Module is closer to top than center
                    y = AlignmentY.Top;
                    yOffset = (yPos);
                }

                break;
            }
            case Bottom: {
                double c = client.getWindow().getScaledHeight() / 3.0;
                double cLeft = client.getWindow().getScaledHeight() / 3.0 * 2;

                if (yPos <= cLeft - height / 2.0) {
                    // Module is closer to center than bottom
                    y = AlignmentY.Center;
                    yOffset = (-c / 2.0 + yPos - c + height / 2.0);
                }

                break;
            }
        }

        if (y == AlignmentY.Top && yOffset < 0) yOffset = 0;
        else if (y == AlignmentY.Bottom && yOffset > 0) yOffset = 0;
    }

    public void setSize(double width, double height) {
        this.width = (width);
        this.height = (height);
    }

    public double getX() {
        switch (x) {
            default:     return xOffset;
            case Center: return (client.getWindow().getScaledWidth() / 2.0 - width / 2.0 + xOffset);
            case Right:  return client.getWindow().getScaledWidth() - width + xOffset;
        }
    }

    public double getY() {
        switch (y) {
            default:     return yOffset;
            case Center: return (client.getWindow().getScaledHeight() / 2.0 - height / 2.0 + yOffset);
            case Bottom: return client.getWindow().getScaledHeight() - height + yOffset;
        }
    }

    public boolean isOver(double x, double y) {
        double sx = getX();
        double sy = getY();

        return x >= sx && x <= sx + width && y >= sy && y <= sy + height;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putString("x", x.name());
        tag.putString("y", y.name());
        tag.putDouble("xOffset", xOffset);
        tag.putDouble("yOffset", yOffset);

        return tag;
    }

    @Override
    public BoundingBox fromTag(NbtCompound tag) {
        x = AlignmentX.valueOf(tag.getString("x"));
        y = AlignmentY.valueOf(tag.getString("y"));

        // It's done this way because before 0.4.2 they were stored as ints
        xOffset = ((NbtInt) tag.get("xOffset")).doubleValue();
        yOffset = ((NbtInt) tag.get("yOffset")).doubleValue();

        return this;
    }
}
