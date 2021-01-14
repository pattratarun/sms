package com.pat.sms;

//@RunWith(SpringRunner.class)
public class AdminServiceTest {/*
	
	@TestConfiguration
	static class AdminServiceImpTestContextConfiguration {

		@Bean
		public AdminService adminService() {
			return new AdminServiceImpl();
		}
	}

	@Autowired
	AdminService adminService;
	
	
	@MockBean
	SchoolRepository schoolRepository;
	
	@MockBean
	private ModelMapper modelMapper;
	
	@Test
	public void whenValidSchoolId_getSchoolDetails_test() {
		String schoolID = UUID.randomUUID().toString();
		School school = new School()
				.setUid(UUID.fromString(schoolID))
				.setName("Shri Rama Bharti School")
				.setStatus("Active")
				.setAddress("Bahadurgarh");
		
		SchoolDto schoolD = new SchoolDto()
				.setUidStr(schoolID)
				.setName("Shri Rama Bharti School")
				.setStatus("Active")
				.setAddress("Bahadurgarh");
		
		
		Optional<School> opt = Optional.of(school);
		
		Mockito.when(schoolRepository.findById(UUID.fromString(schoolID))).thenReturn(opt);
		Mockito.when(modelMapper.map(school, SchoolDto.class)).thenReturn(schoolD);
		
		
		SchoolDto schoolFound = adminService.getSchoolDetails(schoolID);
		
		assertTrue(schoolFound.getUidStr().equals(schoolID));
	}
*/}
