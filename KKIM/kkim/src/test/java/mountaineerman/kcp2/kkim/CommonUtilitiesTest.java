package mountaineerman.kcp2.kkim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommonUtilitiesTest {
    @Test
    void testConvertFloatToInteger() {

        //Minimum
		Assertions.assertEquals(-2147483648, CommonUtilities.convertFloatToInteger((float) -2147483648.0));

		//Maximum
		Assertions.assertEquals(2147483647, CommonUtilities.convertFloatToInteger((float) 2147483647.0));

        //float is less than Integer.MIN_VALUE (-2,147,483,648)
        Assertions.assertEquals(-2147483648, CommonUtilities.convertFloatToInteger((float) -2147483655.0));

        //float is greater than Integer.MAX_VALUE (+2,147,483,647)
		Assertions.assertEquals(2147483647, CommonUtilities.convertFloatToInteger((float) 2147483649.0));

        //Truncation
        Assertions.assertEquals(32, CommonUtilities.convertFloatToInteger((float) 32.9));
    }
}
