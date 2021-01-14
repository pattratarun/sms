package com.pat.sms;

//@RunWith(SpringRunner.class)
public class CandidateServiceImpTest {/*

	@TestConfiguration
	static class CandidateServiceImpTestContextConfiguration {

		@Bean
		public CandidateService candidateService() {
			return new CandidateServiceImpl();
		}
	}

	@Autowired
	CandidateService candidateService;

	@MockBean
	CandidateRepository candidateRepository;

	@MockBean
	ModelMapper modelMapper;

	@Before
	public void setUp() {
		Candidate alex = new Candidate().setFName("Tarun").setLName("Pattra").setAdmissionNum("A2021-001");
		CandidateDto alexd = new CandidateDto().setFName("Tarun").setLName("Pattra").setAdmissionNum("A2021-001");

		Mockito.when(candidateRepository.findByAdmissionNum(alex.getAdmissionNum())).thenReturn(alex);

		Mockito.when(modelMapper.map(alex, CandidateDto.class)).thenReturn(alexd);
	}

	@Test
	public void whenValidName_thenCandidateShouldBeFound() {
		String adminNumber = "A2021-001";
		CandidateDto found = candidateService.getCandidateDetails(adminNumber);
		assertTrue(found.getAdmissionNum().equals(adminNumber));
	}
	
	@Test
	public void whenValid_thenCandidateShouldBeFound() {
		String adminNumber = "A2021-00003";
		String userName = "tarun";
		CandidateDto candidate =new CandidateDto()
				.setAdmissionNum(adminNumber)
				.setFName("Tarun")
				.setLName("Pattra")
				.setAge(36)
				.setBloodGroup("A+")
				.setAddress1("AD-42C, Pitampura, Delhi-34")
				.setCity("New Delhi")
				.setState("Delhi")
				.setCountry("India")
				.setNationality("Indian")
				.setStatus("Active")
				.setStartDay(new Date())
				.setGender("Male")
				.setOnlyChild(true)
				.setIsHostller(false);
		
		
		Candidate newcandidate =new Candidate()
				.setFName("Tarun")
				.setLName("Pattra")
				.setAge(36)
				.setBloodGroup("A+")
				.setAddress1("AD-42C, Pitampura, Delhi-34")
				.setCity("New Delhi")
				.setState("Delhi")
				.setCountry("India")
				.setNationality("Indian")
				.setStatus("Active")
				.setStartDay(new Date())
				.setGender("Male")
				.setOnlyChild(true)
				.setIsHostller(false);
		
		Mockito.when(candidateRepository.count()).thenReturn(2L);
		Mockito.when(modelMapper.map(candidate, Candidate.class)).thenReturn(newcandidate);
		Mockito.when(candidateRepository.save(newcandidate)).thenReturn(newcandidate);
		
		CandidateDto found = candidateService.registerNewCandidate(candidate, userName);
		assertTrue(found.getAdmissionNum().equals(adminNumber));
	}

*/}
