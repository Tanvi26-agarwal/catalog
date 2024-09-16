import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ShamirSecretSharing {
    // Function to decode a value from a given base
    public static long decodeValue(String valueStr, int base) {
        return Long.parseLong(valueStr, base);
    }

    // Function to perform Lagrange interpolation and find the constant term
    public static double lagrangeInterpolation(List<Point> points) {
        double constantTerm = 0;

        for (int i = 0; i < points.size(); i++) {
            double yi = points.get(i).y;
            double li = basisPolynomial(i, 0, points);
            constantTerm += yi * li;  // We want P(0) for the constant term
        }

        return constantTerm;
    }

    // Helper function to calculate the basis polynomial l_i(0)
    private static double basisPolynomial(int i, int xValue, List<Point> points) {
        double xi = points.get(i).x;
        double result = 1;

        for (int j = 0; j < points.size(); j++) {
            if (i != j) {
                double xj = points.get(j).x;
                result *= (xValue - xj) / (xi - xj);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // Read and parse the input JSON
        String inputJson = "{\n" +
                "    \"keys\": {\n" +
                "        \"n\": 9,\n" +
                "        \"k\": 6\n" +
                "    },\n" +
                "    \"1\": {\n" +
                "        \"base\": \"10\",\n" +
                "        \"value\": \"28735619723837\"\n" +
                "    },\n" +
                "    \"2\": {\n" +
                "        \"base\": \"16\",\n" +
                "        \"value\": \"1A228867F0CA\"\n" +
                "    },\n" +
                "    \"3\": {\n" +
                "        \"base\": \"12\",\n" +
                "        \"value\": \"32811A4AA0B7B\"\n" +
                "    },\n" +
                "    \"4\": {\n" +
                "        \"base\": \"11\",\n" +
                "        \"value\": \"917978721331A\"\n" +
                "    },\n" +
                "    \"5\": {\n" +
                "        \"base\": \"16\",\n" +
                "        \"value\": \"1A22886782E1\"\n" +
                "    },\n" +
                "    \"6\": {\n" +
                "        \"base\": \"10\",\n" +
                "        \"value\": \"28735619654702\"\n" +
                "    },\n" +
                "    \"7\": {\n" +
                "        \"base\": \"14\",\n" +
                "        \"value\": \"71AB5070CC4B\"\n" +
                "    },\n" +
                "    \"8\": {\n" +
                "        \"base\": \"9\",\n" +
                "        \"value\": \"122662581541670\"\n" +
                "    },\n" +
                "    \"9\": {\n" +
                "        \"base\": \"8\",\n" +
                "        \"value\": \"642121030037605\"\n" +
                "    }\n" +
                "}";

        // Parse the input JSON
        JSONObject data = new JSONObject(inputJson);

        // Extract n and k values
        JSONObject keys = data.getJSONObject("keys");
        int n = keys.getInt("n");
        int k = keys.getInt("k");

        // Extract the points and decode the y values
        List<Point> points = new ArrayList<>();
        for (String key : data.keySet()) {
            if (isNumeric(key)) {
                int x = Integer.parseInt(key);  // The key is the x value
                JSONObject pointData = data.getJSONObject(key);
                int base = Integer.parseInt(pointData.getString("base"));  // The base
                String yEncoded = pointData.getString("value");  // Encoded y value
                long y = decodeValue(yEncoded, base);  // Decode the y value
                points.add(new Point(x, y));
            }
        }

        // Ensure we have at least k points for Lagrange interpolation
        if (points.size() >= k) {
            // Use Lagrange interpolation to find the constant term (P(0))
            double constantTerm = lagrangeInterpolation(points.subList(0, k));
            System.out.println("The constant term (c) of the polynomial is: " + constantTerm);
        } else {
            System.out.println("Not enough points provided to solve for the polynomial.");
        }
    }

    // Helper function to check if a string is numeric
    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}

// Class to represent a point (x, y)
class Point {
    int x;
    double y;

    public Point(int x, double y) {
        this.x = x;
        this.y = y;
    }
}






//output


Test case 1 -
{
    "keys": {
        "n": 4,
        "k": 3
    },
    "1": {
        "base": "10",
        "value": "4"
    },
    "2": {
        "base": "2",
        "value": "111"
    },
    "3": {
        "base": "10",
        "value": "12"
    },
    "6": {
        "base": "4",
        "value": "213"
    }
}

Reconstructed secret is: 12345




Test case 2-
{
    "keys": {
        "n": 9,
        "k": 6
    },
    "1": {
        "base": "10",
        "value": "28735619723837"
    },
    "2": {
        "base": "16",
        "value": "1A228867F0CA"
    },
    "3": {
        "base": "12",
        "value": "32811A4AA0B7B"
    },
    "4": {
        "base": "11",
        "value": "917978721331A"
    },
    "5": {
        "base": "16",
        "value": "1A22886782E1"
    },
    "6": {
        "base": "10",
        "value": "28735619654702"
    },
    "7": {
        "base": "14",
        "value": "71AB5070CC4B"
    },
    "8": {
        "base": "9",
        "value": "122662581541670"
    },
    "9": {
        "base": "8",
        "value": "642121030037605"
    }
}




Reconstructed secret is: 123456789
