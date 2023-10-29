import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputDto {

    private Integer ipByte3;
    private Integer ipByte2;
    private Integer ipByte1;
    private Integer ipByte0;
    private Integer subnetMask;
    private Integer numThreads;
}
