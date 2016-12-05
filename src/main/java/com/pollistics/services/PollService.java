package com.pollistics.services;

import com.pollistics.models.Poll;
import com.pollistics.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PollService {
	@Autowired
	private PollRepository pollRepo;

	public Poll getPoll(String id) {
		return pollRepo.findOne(id);
	}

	public List<Poll> getAllPolls() {
		return pollRepo.findAll();
	}

	public String createPoll(String title, HashMap<String, Integer> options) {
		Poll poll = pollRepo.insert(new Poll(title, options));
		return poll.getSlug();
	}

	public String createPoll(String title, HashMap<String, Integer> options, String slug) {
		Poll poll = pollRepo.insert(new Poll(title, options, slug));
		return poll.getSlug();
	}

	public boolean deletePoll(String id) {
		try {
			pollRepo.delete(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean voteOption(Poll p, String option) {
		boolean result = p.vote(option);
		if (!result) {
			return false;
		}
		pollRepo.save(p); // todo false when not works
		return true;
	}
}
