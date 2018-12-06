package com.muyuan.platform.skip.common.util;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 范文武
 * @date 2018/12/06 09:44
 */
public class LocationUtil {
    /**
     * 判断点是否在多边形内
     *
     * @param lon 判断点 经度
     * @param lat 判断点 维度
     * @param pts 厂区点集   （点集格式 x,y;x,y;x,y;）
     * @return 点在多边形内返回true, 否则返回false
     */
    public static boolean judge(BigDecimal lon, BigDecimal lat, String pts) {
        List<Point2D.Double> pointList = new ArrayList<>();
        String[] points = pts.split(";");
        Point2D.Double point = new Point2D.Double(lon.doubleValue(), lat.doubleValue());
        for (String point1 : points) {
            String[] pointTest = point1.split(",");
            pointList.add(new Point2D.Double(Double.valueOf(pointTest[0]), Double.valueOf(pointTest[1])));
        }
        return isPtInPoly(point, pointList);
    }

    /**
     * 判断点是否在多边形内
     *
     * @param point 检测点
     * @param pts   多边形的顶点
     * @return 点在多边形内返回true, 否则返回false
     */
    private static boolean isPtInPoly(Point2D.Double point, List<Point2D.Double> pts) {

        int n = pts.size();
        //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        boolean boundOrVertex = true;
        //cross points count of x
        int intersectCount = 0;
        //浮点类型计算时候与0比较时候的容差
        double precision = 2e-10;
        //neighbour bound vertices
        Point2D.Double p1, p2;
        //当前点

        //left vertex
        p1 = pts.get(0);
        //check all rays
        for (int i = 1; i <= n; ++i) {
            if (point.equals(p1)) {
                //p is an vertex
                return true;
            }
            //right vertex
            p2 = pts.get(i % n);
            //ray is outside of our interests
            if (point.x < Math.min(p1.x, p2.x) || point.x > Math.max(p1.x, p2.x)) {
                p1 = p2;
                //next ray left point
                continue;
            }

            //ray is crossing over by the algorithm (common part of)
            if (point.x > Math.min(p1.x, p2.x) && point.x < Math.max(p1.x, p2.x)) {
                //x is before of ray
                if (point.y <= Math.max(p1.y, p2.y)) {
                    //overlies on a horizontal ray
                    if (p1.x == p2.x && point.y >= Math.min(p1.y, p2.y)) {
                        return true;
                    }

                    //ray is vertical
                    if (p1.y == p2.y) {
                        //overlies on a vertical ray
                        if (p1.y == point.y) {
                            return true;
                        } else {
                            //before ray
                            ++intersectCount;
                        }
                    } else {
                        //cross point on the left side
                        //cross point of y
                        double xinters = (point.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;
                        //overlies on a ray
                        if (Math.abs(point.y - xinters) < precision) {
                            return true;
                        }

                        if (point.y < xinters) {
                            //before ray
                            ++intersectCount;
                        }
                    }
                }
            } else {
                //special case when ray is crossing through the vertex
                if (point.x == p2.x && point.y <= p2.y) {
                    //p crossing over p2
                    Point2D.Double p3 = pts.get((i + 1) % n);
                    //next vertex
                    if (point.x >= Math.min(p1.x, p3.x) && point.x <= Math.max(p1.x, p3.x)) {
                        //p.x lies between p1.x & p3.x
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            //next ray left point
            p1 = p2;
        }
        //偶数在多边形外
        return intersectCount % 2 != 0;
    }
}
